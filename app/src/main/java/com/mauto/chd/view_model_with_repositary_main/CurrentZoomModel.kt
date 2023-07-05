package com.mauto.chd.view_model_with_repositary_main

class CurrentZoomModel
{
    var zoomlatitude: String? = null
    var zoomlongitude: String? = null



    constructor(zoomlatitude: String, zoomlongitude: String)
    {
        this.zoomlatitude = zoomlatitude
        this.zoomlongitude = zoomlongitude
    }
}