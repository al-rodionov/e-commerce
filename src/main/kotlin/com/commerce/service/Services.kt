package com.commerce.service

import com.commerce.model.cache.PaymentConfig
import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction

interface ValidatorService {
    fun validate(container: TransactionContainer)
}

interface PaymentConfigService {
    fun getPaymentConfig(paymentMethod: String): PaymentConfig
}

interface TransactionStoreService {
    fun store(container: TransactionContainer)

    fun findAll(): List<Transaction>
}

interface Calculator {
    fun calculate(container: TransactionContainer): Double
}

interface PointsCalcService : Calculator

interface PriceCalcService : Calculator