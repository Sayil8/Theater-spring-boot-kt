package com.example.Theater.Domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Seat(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                val ro: Char,
                val num: Int,
                val price: BigDecimal,
                val description: String) {
    override fun toString(): String = "Seat $ro-$num $$price ($description)"
}