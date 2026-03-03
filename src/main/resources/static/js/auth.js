async function doLogin() {
  const btn   = document.getElementById('login-btn');
  const email = document.getElementById('login-email').value.trim();
  const pass  = document.getElementById('login-password').value;
  const err   = document.getElementById('login-error');
  err.textContent = '';
  setBtnLoading(btn, 'Signing in...', true);
  try {
    const data = await api.login({ email, password: pass });
    saveSession(data.token, data.role, data.email);
    bootApp();
  } catch (e) {
    err.textContent = e.message;
  } finally {
    setBtnLoading(btn, 'Sign In', false);
  }
}

async function doSignup() {
  const btn   = document.getElementById('signup-btn');
  const name  = document.getElementById('signup-name').value.trim();
  const email = document.getElementById('signup-email').value.trim();
  const pass  = document.getElementById('signup-password').value;
  const err   = document.getElementById('signup-error');
  err.textContent = '';
  setBtnLoading(btn, 'Creating account...', true);
  try {
    const data = await api.signup({ name, email, password: pass });
    saveSession(data.token, data.role, data.email);
    bootApp();
  } catch (e) {
    err.textContent = e.message;
  } finally {
    setBtnLoading(btn, 'Create Account', false);
  }
}

// On page load — if already logged in, go straight to app
window.addEventListener('DOMContentLoaded', () => {
  if (getSession().token) bootApp();
});
