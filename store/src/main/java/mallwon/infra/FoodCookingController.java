package mallwon.infra;
import mallwon.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
// @RequestMapping(value="/foodCookings")
@Transactional
public class FoodCookingController {
    @Autowired
    FoodCookingRepository foodCookingRepository;



    @RequestMapping(value = "foodCookings/{id}/acceptorder",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public FoodCooking acceptOrder(@PathVariable(value = "id") Long id, @RequestBody AcceptOrderCommand acceptOrderCommand, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /foodCooking/acceptOrder  called #####");
            Optional<FoodCooking> optionalFoodCooking = foodCookingRepository.findById(id);
            
            optionalFoodCooking.orElseThrow(()-> new Exception("No Entity Found"));
            FoodCooking foodCooking = optionalFoodCooking.get();
            foodCooking.acceptOrder(acceptOrderCommand);
            
            foodCookingRepository.save(foodCooking);
            return foodCooking;
            
    }
    





}
