package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.examly.springapp.exceptions.DuplicateUserException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;
    
    // Inject the JavaMailSender for sending emails via SMTP.
    private final JavaMailSender mailSender;

    public UserServiceImpl(UserRepo userRepo,
            PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public User registerUser(User user) {
        Optional<User> opt = userRepo.findByUserName(user.getUsername());
        if (opt.isPresent()) {
            throw new DuplicateUserException("User already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepo.save(user);
        return newUser;
    }

    @Override
    public User loadUserByUsername(String userName) {
        Optional<User> opt = userRepo.findByUserName(userName);
        if(opt.isEmpty()){
            throw new EntityNotFoundException("User not found with username: " + userName);
        }
        return opt.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getByUserId(int userId) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("UserId not found");
        }
        return opt.get();
    }

    @Override
    public void deleteUser(int userId) {
        userRepo.deleteById(userId);
    }

    public User updateUser(int userId, User user) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("UserId not found");
        }
        user.setUserId(userId);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return userRepo.getUserByName(name);
    }


    public boolean usernameExists(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean mobileExists(String mobileNumber) {
        return userRepo.existsByMobileNumber(mobileNumber);
    }
    
    // ------------------------- OTP Methods -------------------------
    
    // In-memory OTP store. For production, consider using a caching solution with expiry (e.g., Redis).
    private ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();

    /**
     * Sends an OTP to the provided email without using an external API.
     * It generates a 6-digit OTP, stores it in the otpStore,
     * and sends it via email using JavaMailSender.
     *
     * @param email the recipient's email address
     */
    public void sendOtpViaEmail(String email) {
        String otp = generateOtp();
        otpStore.put(email, otp);
        
        // Create a simple email message with the OTP details.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nIt is valid for a limited time.");
        
        try {
            // Send the email using JavaMailSender (SMTP)
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
    
    /**
     * Verifies the provided OTP against the stored OTP for the user's email.
     * If the OTP is correct, it removes the OTP from storage and registers the user.
     *
     * @param user the user to be registered (should contain the email)
     * @param otp  the OTP provided by the user
     * @return the registered User
     * @throws IllegalArgumentException if no OTP is found or if the OTP does not match
     */
    public User verifyOtpAndRegister(User user, String otp) {
        String storedOtp = otpStore.get(user.getEmail());
        if (storedOtp == null) {
            throw new IllegalArgumentException("No OTP found for email " + user.getEmail());
        }
        if (!storedOtp.equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP provided");
        }
        otpStore.remove(user.getEmail());
        return registerUser(user);
    }
    
    /**
     * Helper method to generate a random 6-digit OTP.
     *
     * @return the generated OTP as a string
     */
    private String generateOtp() {
        Random random = new Random();
        int otpInt = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otpInt);
    }
}
