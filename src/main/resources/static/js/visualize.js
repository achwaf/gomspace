// drawing 

const drawSnapshot = (() => {
	let counterFrames = 0;
	let margin = 2;
	let drawMachine = (ctx, x, y, d, sq) => {
		if (sq) ctx.fillRect(x + 4 * margin, y + 4 * margin, 30 - 8 * margin, 30 - 8 * margin);
		else {
			ctx.beginPath();
			if ("DOWN" == d) {
				ctx.moveTo(x + 3 * margin, y + 3 * margin); ctx.lineTo(x + 30 - 3 * margin, y + 3 * margin); ctx.lineTo(x + 15, y + 30 - 3 * margin);
			} else if ("UP" == d) {
				ctx.moveTo(x + 3 * margin, y + 30 - 3 * margin); ctx.lineTo(x + 30 - 3 * margin, y + 30 - 3 * margin); ctx.lineTo(x + 15, y + 3 * margin);
			} else if ("RIGHT" == d) {
				ctx.moveTo(x + 3 * margin, y + 3 * margin); ctx.lineTo(x + 3 * margin, y + 30 - 3 * margin); ctx.lineTo(x + 30 - 3 * margin, y + 15);
			} else if ("LEFT" == d) {
				ctx.moveTo(x + 30 - 3 * margin, y + 3 * margin); ctx.lineTo(x + 30 - 3 * margin, y + 30 - 3 * margin); ctx.lineTo(x + 3 * margin, y + 15);
			}
			ctx.fill();
		}
	}
	return function(snapshot, squareMachine) {
		if (isReset) return;
		let ctx = document.getElementById('grid').getContext('2d');

		// the grid of the snapshot is 20 x 20
		for (let i = 0; i < 20; i++) {
			for (let j = 0; j < 20; j++) {
				if (!snapshot || !snapshot.grid[i][j]) {
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

		// draw the machine
		ctx.fillStyle = "rgb(242, 100, 48)";
		if (!snapshot) {
			drawMachine(ctx, 9 * 30, 9 * 30, "RIGHT", squareMachine);
		} else {
			drawMachine(ctx, snapshot.machineX * 30, snapshot.machineY * 30, snapshot.machineDirection, squareMachine);
		}

		// update frames and moves
		if (!snapshot) {
			counterFrames = 0;
			document.getElementById('frames').innerHTML = "0";
			document.getElementById('moves').innerHTML = "0";
		} else {
			document.getElementById('frames').innerHTML = (++counterFrames).toString();
			document.getElementById('moves').innerHTML = snapshot.move.toString();
		}

	}
})();


// interval caller
let keepCalling = false;
let timeOutCall;
let isReset = false;
const run = (func, time) => {
	if (keepCalling) {
		isReset = false;
		timeOutCall = setTimeout(async function() {
			await func();
			run(func, time);
		}, time);
	}
}

// calling the api
const reset = async () => {
	await fetch('http://localhost:8080/api/simulation/reset');	
	drawSnapshot(null);
	isReset = true;
}

const nextFrame = async () => {
	let response = await fetch('http://localhost:8080/api/simulation/nextFrame');
	let data = await response.json();
	return data;
}

const nextStep = async () => {
	let response = await fetch('http://localhost:8080/api/simulation/nextFrameByStep');
	let data = await response.json();
	return data;
}

const runSimulation = async () => {
	let response = await fetch('http://localhost:8080/api/simulation/after/number/of/iterations', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(input.value)
	});
	let file = await response.blob();
	let fileName = "SimulationAfter" + input.value + "Moves.txt";
	download(file, fileName);

}


// buttons
let runButton;
let stepButton;
let downloadButton;
let input;

// button handlers
let isRunning = false;
const runHandler = () => {
	if (!isRunning) {
		isRunning = true;
		stepButton.disabled = true;
		downloadButton.disabled = true;
		keepCalling = true;
		run(async () => {
			let snapshot = await nextFrame();
			let squareMachine = true;
			drawSnapshot(snapshot, squareMachine);
		}, 20);
	}

}

const resetHandler = () => {
	clearTimeout(timeOutCall);
	keepCalling = false;
	reset();
	runButton.disabled = false;
	stepButton.disabled = false;
	downloadButton.disabled = false;
	isRunning = false;
}

const stepHandler = () => {
	if (!isRunning) {
		isRunning = true;
		runButton.disabled = true;
		downloadButton.disabled = true;
		keepCalling = true;
		run(async () => {
			let snapshot = await nextStep();
			let squareMachine = false;
			drawSnapshot(snapshot, squareMachine);
		}, 400);
	}

}

const downloadHandler = () => {
	let numericValue = parseInt(input.value);
	if (isNaN(numericValue)) numericValue = 100;
	input.value = Math.abs(numericValue);
	runSimulation();
}
// initialization

var callback = function() {
	runButton = document.getElementById('run');
	stepButton = document.getElementById('step');
	downloadButton = document.getElementById('download');
	input = document.getElementById("duration")
	drawSnapshot(null);
};
if (
	document.readyState === "complete" ||
	(document.readyState !== "loading" && !document.documentElement.doScroll)
) {
	callback();
} else {
	document.addEventListener("DOMContentLoaded", callback);
}