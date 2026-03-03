async function request(path, options = {}) {
  const token = localStorage.getItem('dv_token');
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(path, { ...options, headers });

  if (res.status === 401) {         // Token expired
    localStorage.clear();           // Force logout
    window.location.href = '/';
  }
  if (!res.ok) throw new Error(data.error);
  return data;
}

const api = {
  login:          body => request('/auth/login',    { method: 'POST', ... }),
  signup:         body => request('/auth/signup',   { method: 'POST', ... }),
  upload:         form => request('/user/upload',   { method: 'POST', body: form }),
  myDocs:         ()   => request('/user/documents'),
  allUsers:       ()   => request('/admin/users'),
  allDocs:        ()   => request('/admin/documents'),
  updateUser:     (id, body) => request(`/admin/user/${id}`,     { method: 'PUT', ... }),
  updateDocument: (id, body) => request(`/admin/document/${id}`, { method: 'PUT', ... }),
}
