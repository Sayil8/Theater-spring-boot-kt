package com.example.Theater.Data

import com.example.Theater.Domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>