package com.example.Theater.Data

import com.example.Theater.Domain.Booking
import com.example.Theater.Domain.Performance
import com.example.Theater.Domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>

interface PerformanceRepository : JpaRepository<Performance, Long>

interface BookingRepository: JpaRepository<Booking, Long>