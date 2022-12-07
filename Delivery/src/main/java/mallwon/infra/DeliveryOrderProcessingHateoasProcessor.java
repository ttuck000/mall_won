package mallwon.infra;
import mallwon.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class DeliveryOrderProcessingHateoasProcessor implements RepresentationModelProcessor<EntityModel<DeliveryOrderProcessing>>  {

    @Override
    public EntityModel<DeliveryOrderProcessing> process(EntityModel<DeliveryOrderProcessing> model) {

        
        return model;
    }
    
}
