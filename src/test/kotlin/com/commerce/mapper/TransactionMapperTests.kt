package com.commerce.mapper

import com.commerce.model.container.TransactionContainer
import com.commerce.model.entity.Transaction
import com.commerce.util.DATE_TIME
import com.commerce.util.generateTransaction
import com.commerce.util.generateTransactionContainer
import com.commerce.util.parseDate
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionMapperTests {

    @Test
    fun grpcToContainer() {
        val container: TransactionContainer = toContainer(generateTransaction())

        assert(container.customerId == 1L)
        assert(container.price.equals(100.0))
        assert(container.priceModifier.equals(0.95))
        assert(container.dateTime.isEqual(parseDate(DATE_TIME)))

        val additionalItem = container.additionalItem
        assert(additionalItem?.last4?.equals(1212) == true)
        assert(additionalItem?.courier.equals("YAMATO"))

        val bankItem = additionalItem?.bankItem
        assert(bankItem?.bankName.equals("Bank Name 1"))
        assert(bankItem?.accountNumber.equals("1234567"))
        assert(bankItem?.chequeNumber.equals("7654321"))
    }

    @Test
    fun containerToEntity() {
        val container = generateTransactionContainer(
            "CASH",
            0.95,
            TransactionContainer.AdditionalItem(2345, null, null)
        )
        val entity: Transaction = toTranEntity(container)

        assert(entity.customerId.equals(1L))
        assert(entity.price.equals(1000.00))
        assert(entity.priceModifier.equals(0.95))
        assert(entity.dateTime.equals(parseDate(DATE_TIME)))
        assert(entity.additionalItem.equals("last4=2345"))
    }

    @Test
    fun AdditionalItemsDbDescription() {
        val entity = toTranEntity(generateTransactionContainer(
            "VISA", 0.95,
            TransactionContainer.AdditionalItem(5252, null, null)
        ))

        println(entity.additionalItem)

        assert(entity.additionalItem.equals("last4=5252"))
    }
}