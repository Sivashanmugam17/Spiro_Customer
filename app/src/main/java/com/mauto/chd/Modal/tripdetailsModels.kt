package com.mauto.chd.Modal

import com.mauto.chd.ui.sidemenus.questionAnswerModel

class tripdetailsModels {
    var topic: String? = null
    var questionAnswer : ArrayList<questionAnswerModel> ? =  null

    constructor(topic: String, questionAnswer : ArrayList<questionAnswerModel>) {
        this.topic = topic
        this.questionAnswer = questionAnswer
    }
}