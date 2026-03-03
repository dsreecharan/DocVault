// Session helpers
function saveSession(token, role, email) {
  localStorage.setItem('dv_token', token);
  localStorage.setItem('dv_role', role);
  localStorage.setItem('dv_email', email);
}
function getSession() {
  return {
    token: localStorage.getItem('dv_token'),
    role:  localStorage.getItem('dv_role'),
    email: localStorage.getItem('dv_email'),
  };
}
function clearSession() { localStorage.clear(); }

// Toast notifications
function toast(message, type = 'info') {
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.textContent = message;
  document.getElementById('toast-container').appendChild(el);
  setTimeout(() => el.remove(), 3200);
}

// Modals
function openModal(id)  { document.getElementById(id).classList.add('open'); }
function closeModal(id) { document.getElementById(id).classList.remove('open'); }

// Formatting
function fmtDate(dt) {
  if (!dt) return '';
  return new Date(dt).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
}
function getExt(name) { return (name?.split('.').pop() || 'FILE').toUpperCase(); }

// Button loading state
function setBtnLoading(btn, text, loading) {
  btn.disabled = loading;
  btn.innerHTML = loading ? `<span class="spinner"></span>${text}` : text;
}

// Tab switcher (used in auth screen)
function switchTab(tab) {
  document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
  document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
  document.querySelector(`[onclick="switchTab('${tab}')"]`).classList.add('active');
  document.getElementById(`${tab}-tab`).classList.add('active');
}
