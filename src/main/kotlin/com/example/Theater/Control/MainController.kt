package com.example.Theater.Control

import com.example.Theater.Data.PerformanceRepository
import com.example.Theater.Data.SeatRepository
import com.example.Theater.Domain.Booking
import com.example.Theater.Domain.Performance
import com.example.Theater.Domain.Seat
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

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        val model = mapOf("bean" to checkAvailabilityBackingBean(),
                          "performances" to performanceRepository.findAll(),
                            "seatNums" to 1..36,
                            "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("checkAvailability", method = [RequestMethod.POST])
    fun checkAvailability(bean : checkAvailabilityBackingBean) : ModelAndView {
        val selectedSeat : Seat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        bean.seat = selectedSeat
        bean.performance = selectedPerformance

        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.available = result

        if (!result) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }

        val model = mapOf("bean" to bean,
                "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }


    @RequestMapping("booking", method = [RequestMethod.POST])
    fun bookASeat(bean: checkAvailabilityBackingBean) : ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

    @RequestMapping("bootstrap")
    fun createInitData() : ModelAndView {
        val seats = theaterService.seats
        seatRepository.saveAll(seats)

        return homePage()
    }
}

class checkAvailabilityBackingBean() {
    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'
    var selectedPerformance : Long? = null
    var customerName : String = ""

    var available : Boolean? = null
    var seat: Seat? = null
    var performance : Performance? = null
    var booking : Booking? = null
}