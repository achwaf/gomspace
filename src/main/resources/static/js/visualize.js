var drawSnapshot = function() {
	let margin = 2;
	let ctx = document.getElementById('grid').getContext('2d');
	ctx.lineWidth = 2;
	// draw white rects
	ctx.fillStyle = "rgb(179, 236, 255)";
	for (let i = 0; i < 20; i++) {
		for (let j = 0; j < 20; j++) {
			if ((j+3*i) % 17 != 0) {
				ctx.fillStyle = "rgb(179, 236, 255)";
				ctx.fillRect(i * 30 + margin, j * 30 + margin, 30 - 2 * margin, 30 - 2 * margin);
				ctx.fillStyle = "rgb(237, 248, 253)";
				ctx.fillRect(i * 30 + 2 * margin, j * 30 + 2 * margin, 30 - 4 * margin, 30 - 4 * margin);
			} else {
				ctx.fillStyle = "rgb(52, 106, 131)";
				ctx.fillRect(i * 30 + margin, j * 30 + margin, 30 - 2 * margin, 30 - 2 * margin);
				ctx.fillStyle = "rgb(179, 236, 255)";
				ctx.fillRect(i * 30 + 2 * margin, j * 30 + 2 * margin, 30 - 4 * margin, 30 - 4 * margin);
			}
		}
	}
	
	
}


var callback = function() {
	drawSnapshot();
};

if (
	document.readyState === "complete" ||
	(document.readyState !== "loading" && !document.documentElement.doScroll)
) {
	callback();
} else {
	document.addEventListener("DOMContentLoaded", callback);
}