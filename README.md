# Mahila-Shakti Unnati – SHG Micro Finance Management App

Mahila-Shakti Unnati is an offline-first Android application developed to support Women Self-Help Groups (SHGs) in managing savings, loans, repayments, and financial records digitally. The app acts as a digital ledger system that replaces traditional paper-based record keeping and improves financial transparency within SHGs.

## Features

### Member Management

* Add, edit, delete, and view SHG members
* Store member details and profile photos

### Savings Management

* Weekly savings entry tracking
* Paid/Pending status management
* Member-wise savings history

### Loan Management

* Loan application and approval tracking
* Simple interest calculation
* Active/Closed loan status handling

### Repayment Tracking

* Installment payment tracking
* Automatic remaining balance updates
* Loan closure after repayment completion

### Financial Dashboard

* Total group savings
* Active loans
* Pending repayments
* Member financial overview

### Reports

* Financial summary reports
* WhatsApp sharing
* PDF report generation

## Tech Stack

* Kotlin
* Jetpack Compose
* Room Database
* MVVM Architecture
* Material Design 3
* Coroutines
* StateFlow / Flow
* Navigation Component
* Repository Pattern
* Android Intent Sharing

## Architecture

The project follows MVVM Architecture:

User → Compose UI → ViewModel → Repository → Room Database

## Database

Room Database is used for offline data storage with relational entities:

* MemberEntity
* SavingsEntity
* LoanEntity
* RepaymentEntity

Relationships:

* One Member → Many Savings Entries
* One Member → Many Loans
* One Loan → Many Repayments

## Key Functionalities

* Instant dashboard updates
* Loan validation to prevent duplicate unpaid loans
* Automatic repayment balance calculation
* Offline-first functionality
* Beginner-friendly UI

## Future Enhancements

* Firebase Cloud Sync
* Role-Based Access
* Multi-SHG Management
* SMS Payment Reminders
* UPI Payment Integration
* AI-Based Loan Eligibility Prediction

## Developed By

D Sathya

## Project Type

Android App Development using GenAI – Internship Final Project

## License

This project is developed for educational and academic purposes.
