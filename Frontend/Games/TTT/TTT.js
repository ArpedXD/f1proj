const divs = document.querySelectorAll("#main_b div");
let playerChar = '';
let pendingResult = null;

// ── Score tracking (totals across restarts) ──
let totalWins = 0;
let totalLosses = 0;

async function start() {
    // Reset board visuals
    divs.forEach(div => {
        div.innerHTML = '';
        div.classList.remove('x-cell', 'o-cell', 'taken', 'win-cell');
    });
    setTurnIndicator('loading');

    try {
        await fetch("http://localhost:8080/ttt/Start");

        // Get username
        const nameRes = await fetch("http://localhost:8080/base/getUsername");
        const username = await nameRes.text();
        document.getElementById("uname_val").textContent = username;

        // Get score
        const score = await getScore("ttt");
        totalWins = score.wins;
        totalLosses = score.losses;
        updateScoreDisplay();

        // Get player character
        const charRes = await fetch("http://localhost:8080/ttt/player");
        playerChar = await charRes.json();

        if (playerChar === 'O') {
            setTurnIndicator('enemy');
            await enemymove();
            setTurnIndicator('yours');
        } else {
            setTurnIndicator('yours');
        }
    } catch (e) {
        console.error("Start error:", e);
        setTurnIndicator('error');
    }
}

async function board(x, y) {
    if (y.classList.contains('taken')) {
        flashInvalid(y);
        return;
    }

    markCell(y, playerChar);
    setTurnIndicator('enemy');

    try {
        await fetch("http://localhost:8080/ttt/move", {
            method: "POST",
            headers: { "Content-Type": "text/plain" },
            body: String(x)
        });

        const ended = await check();
        if (ended) return;

        await enemymove();
        setTurnIndicator('yours');
        await check();
    } catch (e) {
        console.error("Move error:", e);
    }
}

async function enemymove() {
    try {
        const res = await fetch("http://localhost:8080/ttt/enemymove");
        const idx = await res.json();
        const div = document.querySelector(`#main_b div:nth-child(${idx + 1})`);
        const enemyChar = playerChar === 'X' ? 'O' : 'X';
        markCell(div, enemyChar);
    } catch (e) {
        console.error("Enemy move error:", e);
    }
}

async function check() {
    try {
        const res = await fetch("http://localhost:8080/ttt/Checkwin");
        const result = await res.json();

        if (result === 1) {
            totalWins++;
            updateScoreDisplay();
            await wait(0.4);
            showStatus('win');
            return true;
        } else if (result === 2) {
            totalLosses++;
            updateScoreDisplay();
            await wait(0.4);
            showStatus('lose');
            return true;
        } else if (result === 3) {
            await wait(0.4);
            showStatus('draw');
            return true;
        }
    } catch (e) {
        console.error("Check error:", e);
    }
    return false;
}

// ── Helpers ──

function markCell(div, char) {
    div.innerHTML = char;
    div.classList.add('taken', char === 'X' ? 'x-cell' : 'o-cell');
}

function flashInvalid(div) {
    div.style.borderColor = 'rgba(255,0,200,0.8)';
    setTimeout(() => { div.style.borderColor = ''; }, 300);
}

function setTurnIndicator(state) {
    const el = document.getElementById('turn-indicator');
    el.className = '';
    switch (state) {
        case 'yours':
            el.textContent = `▶ YOUR TURN  [ ${playerChar} ]`;
            el.classList.add('your-turn');
            break;
        case 'enemy':
            el.textContent = '// ENEMY COMPUTING...';
            el.classList.add('enemy-turn');
            break;
        case 'loading':
            el.innerHTML = 'INITIALIZING<span class="blink">...</span>';
            break;
        case 'error':
            el.textContent = 'CONNECTION ERROR';
            el.classList.add('enemy-turn');
            break;
    }
}

function updateScoreDisplay() {
    document.getElementById('win').textContent = totalWins;
    document.getElementById('lose').textContent = totalLosses;

    const total = totalWins + totalLosses || 1;
    const winPct = Math.min((totalWins / total) * 100, 100);
    const losePct = Math.min((totalLosses / total) * 100, 100);

    document.getElementById('wins-bar').style.width = winPct + '%';
    document.getElementById('loses-bar').style.width = losePct + '%';
}

function showStatus(type) {
    const overlay = document.getElementById('status-overlay');
    const box = document.getElementById('status-box');
    const text = document.getElementById('status-text');
    const sub = document.getElementById('status-sub');

    box.className = '';
    overlay.classList.add('show');

    if (type === 'win') {
        box.classList.add('');
        text.style.color = 'var(--cyan)';
        text.style.textShadow = 'var(--glow-cyan)';
        text.textContent = 'VICTORY';
        sub.textContent = '// TARGET ELIMINATED';
    } else if (type === 'lose') {
        box.classList.add('lose-box');
        text.style.color = 'var(--magenta)';
        text.style.textShadow = 'var(--glow-magenta)';
        text.textContent = 'DEFEATED';
        sub.textContent = '// SYSTEM OVERRIDE';
    } else {
        box.classList.add('draw-box');
        text.style.color = 'rgba(255,220,0,0.9)';
        text.style.textShadow = '0 0 8px rgba(255,200,0,0.6)';
        text.textContent = 'STALEMATE';
        sub.textContent = '// NO VICTOR DETECTED';
    }
}

function dismissStatus() {
    document.getElementById('status-overlay').classList.remove('show');
    start();
}

function wait(sec) {
    return new Promise(r => setTimeout(r, sec * 1000));
}

start();