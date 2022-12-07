package mallwon.infra;
import mallwon.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class CheckOederAdressHateoasProcessor implements RepresentationModelProcessor<EntityModel<CheckOederAdress>>  {

    @Override
    public EntityModel<CheckOederAdress> process(EntityModel<CheckOederAdress> model) {

        
        return model;
    }
    
}
