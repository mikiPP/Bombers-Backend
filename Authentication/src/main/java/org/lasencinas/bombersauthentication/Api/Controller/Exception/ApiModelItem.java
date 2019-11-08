package org.lasencinas.bombersauthentication.Api.Controller.Exception;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public abstract class ApiModelItem implements Serializable {
    public ApiModelItem() {
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }
}
