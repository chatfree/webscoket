# 简单webscoket
##js测试方法
 ws = new WebSocket("ws://localhost:8888/chat");
  // 建立 web socket 连接成功触发事件
  ws.onopen = function () {
    // 使用 send() 方法发送数据
    ws.send("client发送数据");
  };

  // 接收服务端数据时触发事件
  ws.onmessage = function (evt) {
    var received_msg = evt.data;
    alert(received_msg);
  };

  // 断开 web socket 连接成功触发事件
  ws.onclose = function () {
    alert("连接已关闭...");
  };