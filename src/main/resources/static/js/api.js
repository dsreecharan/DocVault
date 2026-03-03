async function request(path, options = {}) {
  const token = localStorage.getItem('dv_token');
  const headers = { 'Content-Type': 'application/json' };
  if (token) headers['Authorization'] = `Bearer ${token}`;
  if (options.body instanceof FormData) delete headers['Content-Type'];

  const res = await fetch(path, { ...options, headers });
  const data = res.headers.get('content-type')?.includes('json') ? await res.json() : {};

  if (res.status === 401) {
    localStorage.clear();
    window.location.href = '/';
    return;
  }
  if (!res.ok) throw new Error(data.error || 'Request failed');
  return data;
}

const api = {
  login:          b => request('/auth/login',   { method: 'POST', body: JSON.stringify(b) }),
  signup:         b => request('/auth/signup',  { method: 'POST', body: JSON.stringify(b) }),
  upload:         f => request('/user/upload',  { method: 'POST', body: f }),
  myDocs:         () => request('/user/documents'),
  allUsers:       () => request('/admin/users'),
  allDocs:        () => request('/admin/documents'),
  updateUser:     (id, b) => request(`/admin/user/${id}`,     { method: 'PUT', body: JSON.stringify(b) }),
  updateDocument: (id, b) => request(`/admin/document/${id}`, { method: 'PUT', body: JSON.stringify(b) }),
};
