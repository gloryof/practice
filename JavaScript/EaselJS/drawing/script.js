var Drawing = new Vue({
    el: "#drawing",
    data: {
        canvas: {
            mode: {
                erase: false
            },
            stage: null,
            shape: null,
            listener: null,
            point: {
                x: null,
                y: null
            }
        }
    },
    ready: function() {

        var stage = new window.createjs.Stage("canvas-area");
        window.createjs.Touch.enable(stage);

        var shape = stage.addChild(new window.createjs.Shape());
        shape.cache(0, 0, 600, 400);

        stage.on("stagemousedown", this._startDraw, this);

        this.canvas.stage = stage;
        this.canvas.shape = shape;
    },
    methods: {
        clear: function() {
            this.canvas.stage.removeAllChildren();

            var shape = this.canvas.stage.addChild(new window.createjs.Shape());
            shape.cache(0, 0, 600, 400);

            this.canvas.shape = shape;

            this.canvas.stage.update();
        },
        changeToPencil: function() {
            this.canvas.mode.erase = false;
        },
        changeToEraser: function() {
            this.canvas.mode.erase = true;
        },
        load: function() {

            var img=new Image();
            var that = this;
            img.onload=function() {

                that.canvas.stage.removeAllChildren();

                var shape = that.canvas.stage.addChild(new window.createjs.Shape());
                shape.cache(0, 0, 600, 400);

                shape.graphics.beginBitmapFill(img).drawRoundRect(0,0,600,400,5);
                shape.updateCache("source-over");
                shape.graphics.clear();

                that.canvas.shape = shape;
                that.canvas.stage.update();
            };
            img.src="sample.png";
        },
        _startDraw: function(event) {

            var listener = this.canvas.stage.on("stagemousemove", this._draw, this);
            this.canvas.stage.on("stagemouseup", this._endDraw, this);

            this.canvas.point.x = event.x;
            this.canvas.point.y = event.y;
            this.canvas.listener = listener;
        },
        _draw :function(event) {
            this.canvas.shape.graphics
                .setStrokeStyle(10, 1)
                .beginStroke(window.createjs.Graphics.getRGB(120, 0, 0))
                .moveTo(this.canvas.point.x, this.canvas.point.y)
                .lineTo(event.stageX, event.stageY);

            this.canvas.shape.updateCache(this.canvas.mode.erase ? "destination-out" : "source-over");

            this.canvas.shape.graphics.clear();

            this.canvas.point.x = event.stageX;
            this.canvas.point.y = event.stageY;
            this.canvas.stage.update();
        },
        _endDraw: function(event) {

            this.canvas.stage.off("stagemousemove", this.canvas.listener);
            event.remove();
        }
    }
});
