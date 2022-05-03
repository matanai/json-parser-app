package com.app.model;

import com.app.constant.Field;
import com.app.constant.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    private Field searchField = Field.DEFAULT;
    private Field sortField = Field.DEFAULT;
    private Operator operator = Operator.DEFAULT;
    private Object value;
    private boolean sortAscending = true;
}
