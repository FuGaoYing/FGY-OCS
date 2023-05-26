const eventNameData = 'data';
let mySocket;
function Result(code, message, data) {
    return {
        code: code,
        message: message,
        data: data
    }
}

function initSocket(token) {
    if (mySocket != null && mySocket.connected) {
        return;
    }
    mySocket = io.connect("http://localhost:9878/",{
        // 关闭自动重连
        reconnection: false,
        query: {
            auth: token
        }

    });
    mySocket.io.on("error", (error) => {
        console.log("连接错误",error);
    });
    mySocket.io.on("reconnect", (attempt) => {
        console.log("成功重新连接尝试次数",attempt);
    });

    mySocket.io.on("reconnect_attempt", (attempt) => {
        console.log("尝试重新连接次数",attempt);
    });

    mySocket.io.on("reconnect_error", (error) => {
        console.log("重新连接尝试错误",error);
    });

    mySocket.io.on("reconnect_failed", () => {
        console.log("无法在reconnectionAttempts内重新连接")
    });

    mySocket.on(eventNameData, (data,ack) => {
        console.log("data",data);
        ack("收到消息");
    });


    mySocket.on("connect", () => {
        console.log("建立socket连接" +mySocket.id);
        // const engine = mySocket.io.engine;
        // console.log("transport.name" +engine.transport.name); // in most cases, prints "polling"
        //
        // engine.once("upgrade", () => {
        //     // called when the transport is upgraded (i.e. from http long-polling to webmySocket)
        //     console.log("upgrade" + engine.transport.name); // in most cases, prints "webmySocket"
        // });
        //
        // engine.on("packet", ({ type, data }) => {
        //     // called for each packet received
        //     console.log("packet",type,data);
        // });
        //
        // engine.on("packetCreate", ({ type, data }) => {
        //     // called for each packet sent
        //     console.log("packetCreate",type,data);
        // });
        //
        // engine.on("drain", () => {
        //     // called when the write buffer is drained
        //     console.log("drain");
        // });
        //
        // engine.on("close", (reason) => {
        //     // called when the underlying connection is closed
        //     console.log("close",reason);
        // });
    });
    mySocket.on("disconnect", (reason) => {
        console.log("disconnect",reason);
        if (reason === "io server disconnect" || reason === "io client disconnect") {
            console.log("会话挂断" ,reason)
            mySocket = null;
        }
        tryReconnect();

    });

    mySocket.on("connect_error", (error) => {
        console.log("connect_error",error);
        mySocket = null;
    });

    mySocket.io.on("ping", () => {
        console.log("io ping")
    });

    mySocket.io.on("pong", () => {
        console.log("io pong")
    });

    // 为名为listener的事件添加一次性eventName函数。下一次触发eventName时，这个监听器将被移除，然后被调用。
    mySocket.once("my-event", () => {
        // ...
    });

}

const tryReconnect = () => {
    setTimeout(() => {
        mySocket.io.open((err) => {
            if (err) {
                tryReconnect();
            }
        });
    }, 500);
}
function login(name, password) {
    initSocket();
}

/**
 * 指定事件名发送
 * @param eventName 事件名
 * @param message 消息体
 * @param callback 回调
 */
function emitMessage(eventName,message,callback) {
    if (mySocket != null && mySocket.connected){
        mySocket.emit(eventName, message, (response) => {
            console.log(response);
            callback(response);
        });
    }else {
        return callback(Result(500,"连接不存在",null));
    }
}

/**
 * 发送到默认事件名
 * @param message 消息
 * @param callback 回调
 */
function sendMessage(message,callback) {
    if (mySocket != null){
        if (!mySocket.connected) {
            mySocket.connect();
        }
        mySocket.send(message,(response) => {
            console.log(response);
            return callback(response);
        })
    } else {
        return callback(Result(500,"连接不存在",null));
    }
}