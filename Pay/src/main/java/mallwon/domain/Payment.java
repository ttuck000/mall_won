package mallwon.domain;

import mallwon.domain.Paid;
import mallwon.domain.PaymentCanceled;
import mallwon.domain.Paymentrefunded;
import mallwon.PayApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Payment_table")
@Data

public class Payment  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private Long orderId;
    
    
    
    
    
    private Integer orderAmout;
    
    
    
    
    
    private Date approveTime;
    
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        Paid paid = new Paid(this);
        paid.publishAfterCommit();



        PaymentCanceled paymentCanceled = new PaymentCanceled(this);
        paymentCanceled.publishAfterCommit();



        Paymentrefunded paymentrefunded = new Paymentrefunded(this);
        paymentrefunded.publishAfterCommit();

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = PayApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }




    public static void paymentCancel(UnavailableArea unavailableArea){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
        paymentCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(unavailableArea.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);

            PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
            paymentCanceled.publishAfterCommit();

         });
        */

        
    }
    public static void paymentCancel(OederCanceled oederCanceled){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
        paymentCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(oederCanceled.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);

            PaymentCanceled paymentCanceled = new PaymentCanceled(payment);
            paymentCanceled.publishAfterCommit();

         });
        */

        
    }
    public static void paymentrefund(Deliverytimeout deliverytimeout){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        Paymentrefunded paymentrefunded = new Paymentrefunded(payment);
        paymentrefunded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(deliverytimeout.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);

            Paymentrefunded paymentrefunded = new Paymentrefunded(payment);
            paymentrefunded.publishAfterCommit();

         });
        */

        
    }
    public static void pay(OrderableArea orderableArea){

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        Paid paid = new Paid(payment);
        paid.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderableArea.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);

            Paid paid = new Paid(payment);
            paid.publishAfterCommit();

         });
        */

        
    }


}
