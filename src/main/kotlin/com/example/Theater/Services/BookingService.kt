package com.example.Theater.Services

import com.example.Theater.Data.BookingRepository
import com.example.Theater.Data.SeatRepository
import com.example.Theater.Domain.Booking
import com.example.Theater.Domain.Performance
import com.example.Theater.Domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    fun isSeatFree(seat : Seat, performance: Performance) : Boolean {
        val isAvailable = findBooking(seat, performance)
        return isAvailable == null

    }

    fun findSeat(seatNum: Int, seatRow: Char) : Seat? {
        val seats = seatRepository.findAll()
        val foundSeat = seats.filter { it.ro == seatRow && it.num == seatNum }
        return foundSeat.firstOrNull()
    }

    fun findBooking(seat: Seat, performance: Performance) : Booking? {
        val bookings = bookingRepository.findAll()
        return bookings.filter { it.seat == seat && it.performance == performance }.firstOrNull()
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String) : Booking {
        var booking = Booking(0, customerName)
        booking.seat = seat
        booking.performance = performance
        bookingRepository.save(booking)
        return booking
    }
}