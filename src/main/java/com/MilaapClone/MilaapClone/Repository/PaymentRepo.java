package com.MilaapClone.MilaapClone.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MilaapClone.MilaapClone.Model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository <Payment ,Long> {

    //Payment save(Payment payment);

}