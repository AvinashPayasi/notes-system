package com.notes.system.api.convertor;

import com.notes.system.api.entity.enums.NotesState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpringToNotesStateConvertor implements Converter<String, NotesState> {

    @Override
    public NotesState convert(String str){
        return NotesState.valueOf(str.toUpperCase());
    }
}
