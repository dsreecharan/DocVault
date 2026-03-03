const USER_PAGES  = [{ id:'page-upload', label:'Upload Document' }, { id:'page-docs', label:'My Documents' }];
const ADMIN_PAGES = [{ id:'page-admin-users', label:'Users' }, { id:'page-admin-docs', label:'Documents' }];

let allUsersData = [];
let allDocsData  = [];
let myDocsData   = [];

function bootApp() {
  const { role, email } = getSession();
  document.getElementById('auth-screen').style.display = 'none';
  document.getElementById('app-screen').style.display = 'flex';
  document.getElementById('sidebar-email').textContent = email;
  const badge = document.getElementById('sidebar-role');
  badge.textContent = role;
  badge.className = `badge ${role === 'ADMIN' ? 'admin' : ''}`;
  buildNav(role);
  const firstPage = role === 'ADMIN' ? 'page-admin-users' : 'page-upload';
  showPage(firstPage);
}

function buildNav(role) {
  const pages = role === 'ADMIN' ? ADMIN_PAGES : USER_PAGES;
  const nav = document.getElementById('sidebar-nav');
  nav.className = 'sidebar-nav';
  nav.innerHTML = pages.map(p =>
    `<button class="nav-btn" id="nav-${p.id}" onclick="showPage('${p.id}')">${p.label}</button>`
  ).join('');
}

function showPage(pageId) {
  document.querySelectorAll('.page').forEach(p => p.style.display = 'none');
  document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
  const page = document.getElementById(pageId);
  if (page) page.style.display = 'block';
  const btn = document.getElementById(`nav-${pageId}`);
  if (btn) btn.classList.add('active');
  if (pageId === 'page-docs')        loadMyDocs();
  if (pageId === 'page-admin-users') loadAdminUsers();
  if (pageId === 'page-admin-docs')  loadAdminDocs();
}

// ── UPLOAD ──────────────────────────────────────────────────────
function handleDrop(e) {
  e.preventDefault();
  const file = e.dataTransfer.files[0];
  if (file) { document.getElementById('file-input').files = e.dataTransfer.files; doUpload(); }
}

async function doUpload() {
  const fileInput = document.getElementById('file-input');
  const file = fileInput.files[0];
  if (!file) return;
  const prog  = document.getElementById('upload-progress');
  const fill  = document.getElementById('progress-fill');
  const label = document.getElementById('progress-label');
  const result= document.getElementById('upload-result');
  prog.style.display = 'block';
  result.innerHTML = '';
  let pct = 0;
  const interval = setInterval(() => {
    pct = Math.min(pct + 8, 85);
    fill.style.width = pct + '%';
    label.textContent = pct < 40 ? 'Uploading...' : pct < 70 ? 'Extracting text...' : 'Generating summary...';
  }, 400);
  try {
    const fd = new FormData();
    fd.append('file', file);
    const doc = await api.upload(fd);
    clearInterval(interval);
    fill.style.width = '100%';
    label.textContent = 'Done!';
    setTimeout(() => { prog.style.display = 'none'; fill.style.width = '0'; }, 1000);
    result.innerHTML = `
      <div class="doc-card" style="margin-top:24px">
        <div class="doc-card-name">${doc.fileName}</div>
        <div class="doc-card-meta">Uploaded just now</div>
        <div class="doc-card-summary">${doc.summary || 'No summary available.'}</div>
      </div>`;
    toast('Document uploaded!', 'success');
  } catch (e) {
    clearInterval(interval);
    prog.style.display = 'none';
    toast(e.message, 'error');
  }
  fileInput.value = '';
}

// ── MY DOCUMENTS ────────────────────────────────────────────────
async function loadMyDocs() {
  try { myDocsData = await api.myDocs(); renderDocs(myDocsData); }
  catch (e) { toast(e.message, 'error'); }
}
function filterDocs() {
  const q = document.getElementById('docs-search').value.toLowerCase();
  renderDocs(myDocsData.filter(d => d.fileName?.toLowerCase().includes(q)));
}
function renderDocs(docs) {
  const grid = document.getElementById('docs-grid');
  if (!docs.length) { grid.innerHTML = '<p style="color:#888">No documents yet.</p>'; return; }
  grid.innerHTML = docs.map(d => `
    <div class="doc-card">
      <div class="doc-card-name">${d.fileName}</div>
      <div class="doc-card-meta">${fmtDate(d.uploadedAt)}</div>
      <div class="doc-card-summary">${d.summary || 'No summary.'}</div>
    </div>`).join('');
}

// ── ADMIN USERS ─────────────────────────────────────────────────
async function loadAdminUsers() {
  try {
    allUsersData = await api.allUsers();
    const total = allUsersData.length;
    const admins= allUsersData.filter(u => u.role === 'ADMIN').length;
    document.getElementById('user-stats').innerHTML = `
      <div class="stat-card"><div class="stat-value">${total}</div><div class="stat-label">Total Users</div></div>
      <div class="stat-card"><div class="stat-value">${total-admins}</div><div class="stat-label">Regular Users</div></div>
      <div class="stat-card"><div class="stat-value">${admins}</div><div class="stat-label">Admins</div></div>`;
    renderUsersTable(allUsersData);
  } catch(e) { toast(e.message,'error'); }
}
function filterUsers() {
  const q = document.getElementById('users-search').value.toLowerCase();
  renderUsersTable(allUsersData.filter(u => u.name?.toLowerCase().includes(q) || u.email?.toLowerCase().includes(q)));
}
function renderUsersTable(users) {
  document.getElementById('users-table-wrap').innerHTML = `
    <table class="data-table">
      <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Action</th></tr></thead>
      <tbody>${users.map(u => `
        <tr>
          <td>${u.name||''}</td><td>${u.email}</td>
          <td><span class="badge ${u.role==='ADMIN'?'admin':''}">${u.role}</span></td>
          <td><button class="btn btn-ghost" style="padding:4px 12px;font-size:.8rem" onclick="openEditUser('${u.id}','${u.name}','${u.email}','${u.role}')">Edit</button></td>
        </tr>`).join('')}
      </tbody></table>`;
}
function openEditUser(id,name,email,role) {
  document.getElementById('edit-user-id').value    = id;
  document.getElementById('edit-user-name').value  = name;
  document.getElementById('edit-user-email').value = email;
  document.getElementById('edit-user-role').value  = role;
  openModal('modal-edit-user');
}
async function saveUser() {
  const id    = document.getElementById('edit-user-id').value;
  const name  = document.getElementById('edit-user-name').value;
  const email = document.getElementById('edit-user-email').value;
  const role  = document.getElementById('edit-user-role').value;
  try {
    await api.updateUser(id, { name, email, role });
    closeModal('modal-edit-user');
    toast('User updated', 'success');
    loadAdminUsers();
  } catch(e) { toast(e.message,'error'); }
}

// ── ADMIN DOCUMENTS ─────────────────────────────────────────────
async function loadAdminDocs() {
  try { allDocsData = await api.allDocs(); renderDocsTable(allDocsData); }
  catch(e) { toast(e.message,'error'); }
}
function filterAdminDocs() {
  const q = document.getElementById('admin-docs-search').value.toLowerCase();
  renderDocsTable(allDocsData.filter(d => d.fileName?.toLowerCase().includes(q)));
}
function renderDocsTable(docs) {
  document.getElementById('admin-docs-table-wrap').innerHTML = `
    <table class="data-table">
      <thead><tr><th>File</th><th>Uploaded By</th><th>Date</th><th>Action</th></tr></thead>
      <tbody>${docs.map(d => `
        <tr>
          <td>${d.fileName}</td><td>${d.uploadedBy}</td><td>${fmtDate(d.uploadedAt)}</td>
          <td><button class="btn btn-ghost" style="padding:4px 12px;font-size:.8rem" onclick="openEditDoc('${d.id}','${d.fileName?.replace(/'/g,"\\'")}')">Edit</button></td>
        </tr>`).join('')}
      </tbody></table>`;
}
function openEditDoc(id, name) {
  const doc = allDocsData.find(d => d.id === id);
  document.getElementById('edit-doc-id').value      = id;
  document.getElementById('edit-doc-name').value    = name;
  document.getElementById('edit-doc-summary').value = doc?.summary || '';
  openModal('modal-edit-doc');
}
async function saveDoc() {
  const id      = document.getElementById('edit-doc-id').value;
  const name    = document.getElementById('edit-doc-name').value;
  const summary = document.getElementById('edit-doc-summary').value;
  try {
    await api.updateDocument(id, { fileName: name, summary });
    closeModal('modal-edit-doc');
    toast('Document updated', 'success');
    loadAdminDocs();
  } catch(e) { toast(e.message,'error'); }
}

function doLogout() {
  clearSession();
  document.getElementById('app-screen').style.display = 'none';
  document.getElementById('auth-screen').style.display = 'flex';
}