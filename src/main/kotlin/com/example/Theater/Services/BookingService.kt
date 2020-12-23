package com.example.Theater.Services

import com.example.Theater.Domain.Seat
import org.springframework.stereotype.Service

@Service
class BookingService {

    fun isSeatFree(seat : Seat) : Boolean {
        return true
    }
}