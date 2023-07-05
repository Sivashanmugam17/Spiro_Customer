package com.mauto.chd.Modal

class VerficationModel
{
    var id: String? = null
    var name: String? = null
    var fileName: String? = null
    var expiryDate: String? = null
    var type: String? = null
    var hasExp: String? = null

    constructor(idstring: String, namestring: String, fileNamestring: String, expiryDatestring: String, typestring: String, hasexpstring: String)
    {
        this.id = idstring
        this.name = namestring
        this.fileName = fileNamestring
        this.expiryDate = expiryDatestring
        this.type = typestring
        this.hasExp = hasexpstring
    }
}