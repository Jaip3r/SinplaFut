package com.team.service.exception;

public class AssociatedEntitiesException extends RuntimeException {

    String resourceName;

    String resourceValue;

    String associatedEntity;

    public AssociatedEntitiesException(String resourceName, String resourceValue, String associatedEntity){
        super(String.format("Acción no permitida, El %s %s contiene %s asociados", resourceName, resourceValue, associatedEntity));
        this.resourceName = resourceName;
        this.resourceValue = resourceValue;
        this.associatedEntity = associatedEntity;
    }

    
}
