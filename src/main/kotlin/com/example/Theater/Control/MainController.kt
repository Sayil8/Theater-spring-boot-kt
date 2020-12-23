package com.example.Theater.Control

import com.example.Theater.Data.SeatRepository
import com.example.Theater.Services.BookingService
import com.example.Theater.Services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    lateinit var theaterService : TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        return ModelAndView("seatBooking", "bean", checkAvailabilityBackingBean())
    }

    @RequestMapping("checkAvailability", method = [RequestMethod.POST])
    fun checkAvailability(bean : checkAvailabilityBackingBean) : ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val result = bookingService.isSeatFree(selectedSeat)
        bean.result = "Seat $selectedSeat is " + if(result) "available" else "booked"

        return ModelAndView("seatBooking", "bean", bean)
    }

    @RequestMapping("bootstrap")
    fun createInitData() : ModelAndView {
        val seats = theaterService.seats
        seatRepository.saveAll(seats)

        return homePage()
    }
}

class checkAvailabilityBackingBean() {
    val seatNums = 1..36
    val SeatRows = 'A'..'O'

    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'

    var result: String = ""
}