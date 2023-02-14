package com.commerce.service.impl

import com.commerce.exception.ValidationException
import com.commerce.model.cache.PaymentConfig
import com.commerce.model.entity.PaymentMethod
import com.commerce.repo.PaymentMethodRepository
import com.commerce.service.PaymentConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("server")
class PaymentConfigServiceImpl @Autowired constructor(
    val repository: PaymentMethodRepository
) : PaymentConfigService {

    fun findAll(): List<PaymentMethod> {
        return repository.findAll()
    }

    override fun getPaymentConfig(paymentMethod: String): PaymentConfig {
        return getPaymentsConfigs().find { it.name.equals(paymentMethod) }
            ?: throw ValidationException("Invalid payment method")
    }

    private fun getPaymentsConfigs(): List<PaymentConfig> {
        return listOf<PaymentConfig>(
            PaymentConfig("CASH", 0.9, 1.0, 0.05),
            PaymentConfig("CASH_ON_DELIVERY", 1.0, 1.02, 0.05),
            PaymentConfig("VISA", 0.95, 1.0, 0.03),
            PaymentConfig("BANK_TRANSFER", 1.0, 1.0, 0.0),
            PaymentConfig("CHEQUE", 0.9, 1.0, 0.0),
        )
    }
}