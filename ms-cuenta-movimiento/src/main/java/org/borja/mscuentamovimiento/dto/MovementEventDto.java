package org.borja.mscuentamovimiento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MovementEventDto {
    
    private Long movementId;
    private Long accountId;
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String movementType;
    private Double amount;
    private Double balance;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private String accountNumber;
    
    // Constructors
    public MovementEventDto() {}
    
    public MovementEventDto(Long movementId, Long accountId, Long clientId, 
                           String clientName, String clientEmail, String movementType, 
                           Double amount, Double balance, LocalDate date, String accountNumber) {
        this.movementId = movementId;
        this.accountId = accountId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.movementType = movementType;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
        this.accountNumber = accountNumber;
    }
    
    // Getters and Setters
    public Long getMovementId() {
        return movementId;
    }
    
    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }
    
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getClientEmail() {
        return clientEmail;
    }
    
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    
    public String getMovementType() {
        return movementType;
    }
    
    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public Double getBalance() {
        return balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    @Override
    public String toString() {
        return "MovementEventDto{" +
                "movementId=" + movementId +
                ", accountId=" + accountId +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", movementType='" + movementType + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                ", date=" + date +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
