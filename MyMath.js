"use strict";


const MongoClient = require("mongodb").MongoClient;


export class MyMath {

    constructor() {
        this.acc = 0;
    };

    accum(x) {
        this.acc += x;
    };

    getAccum() {
        return this.acc;
    }

    onClickEvent(evt) {
        this.acc ++;
    }
}

MyMath.PI = 3.1415;

