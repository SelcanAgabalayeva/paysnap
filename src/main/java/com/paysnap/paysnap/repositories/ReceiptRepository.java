package com.paysnap.paysnap.repositories;

import com.paysnap.paysnap.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
}
