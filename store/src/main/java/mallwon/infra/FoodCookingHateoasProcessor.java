package mallwon.infra;
import mallwon.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class FoodCookingHateoasProcessor implements RepresentationModelProcessor<EntityModel<FoodCooking>>  {

    @Override
    public EntityModel<FoodCooking> process(EntityModel<FoodCooking> model) {
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/acceptorder").withRel("acceptorder"));

        
        return model;
    }
    
}
