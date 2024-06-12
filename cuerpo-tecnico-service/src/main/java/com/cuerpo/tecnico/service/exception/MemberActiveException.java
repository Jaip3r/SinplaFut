package com.cuerpo.tecnico.service.exception;

public class MemberActiveException extends RuntimeException {

    String memberName;

    String memberLastName;

    public MemberActiveException(String memberName, String memberLastName){
        super(String.format("El integrante del cuerpo técnico %s %s ya se encuentra actualmente activo en un equipo", memberName, memberLastName));
        this.memberName = memberName;
        this.memberLastName = memberLastName;
    }
    
}
