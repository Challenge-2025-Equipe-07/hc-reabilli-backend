package br.com.ccg.enms;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RelatedType {
    VIDEO,
    TEXT;


    public static RelatedType fromText(String enummerator){
        return RelatedType.valueOf(enummerator.trim().toUpperCase());
    }
}
