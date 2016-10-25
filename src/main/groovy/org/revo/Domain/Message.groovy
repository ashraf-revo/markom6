package org.revo.Domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

import javax.persistence.Entity
import javax.validation.constraints.NotNull

/**
 * Created by ashraf on 30/08/16.
 */
@Document
//@Entity
class Message {
    @Id
    String id
    String content
    @CreatedDate
    Date createdate
    @DBRef
    @NotNull
    @CreatedBy
    User from
    @DBRef
    @NotNull
    User to
    String image
    @Transient
    String file

}