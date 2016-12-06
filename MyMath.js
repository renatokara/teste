"use strict";


const MongoClient = require("mongodb").MongoClient;

// Oi


// Oi 2
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


// Oi 3
MyMath.PI = 3.1415;

