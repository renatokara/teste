"use strict";

const asyncWrap = function (fn) {
    return Promise.coroutine(fn);
} ;

const asyncExec = function (fn) {
    return asyncWrap(fn)();
};

function buscaPecas(db, qtd) {
    return db.collection("pieces").find().limit(qtd);
}

function buscaStorePorId(db, store_id) {
    return db.collection("stores").findOne({_id: store_id});
}

/// -------

const escopoFuncao = {};

MongoClient.connect()
    .then((db) => {
        let prom1 = buscaPecas(200)
            .then((docPecas) => {
                const arrPromises = docPecas.map((peca) => buscaStorePorId(peca.store_id));
                return Promise.all(arrPromises);
            })
            .then((docStores) => {
                console.log(docStores);
            })
            .then(() => {
                db.close();
            });

    })
    .catch((err) => {
        console.log("erro", err)
    });



async function teste2() {
    const urls = ["http://google.com?search=renato", "http://bing.com?query=renato", "http://yahoo.com?q=renato"];

    const promisesArray = urls.map((url) => buscaDadosUrl(url));
    promisesArray.push(Promise.delay(5000).then(()=>Promise.reject()));

    await Promise.race(promisesArray).then((data)=>{
        console.log("Encontrei", data);
    });

}




const prom1 = (async function () {
    try {
        const db = await Promise.race([MongoClient.connect(), Promise.delay(5000).then(()=>Promise.reject())]);
        const docPecas = await buscaPecas(200);
        const arrPromises = docPecas.map((peca) => buscaStorePorId(peca.store_id));
        const docStores = await Promise.all(arrPromises);
        await db.close();
    } catch (err){
        console.log("erro", err)
    }
})();


