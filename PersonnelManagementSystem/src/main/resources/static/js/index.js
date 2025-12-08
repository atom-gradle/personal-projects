// 使用事件委托监听整个下拉菜单
document.getElementById('userDropdown').addEventListener('click', function(e) {
    const target = e.target.closest('.dropdown-item');
    if (!target) return;

    e.preventDefault();

    const action = target.getAttribute('data-action');

    switch(action) {
        case 'settings':
            handleSettings();
            break;
        case 'logout':
            handleLogout();
            break;
    }
});

function handleSettings() {
    console.log('打开设置');
    // 设置逻辑
    // 例如：显示设置模态框
    // openSettingsModal();
}

async function handleLogout() {
    if (confirm('确定要退出登录吗？')) {
        const params = new URLSearchParams(window.location.search);
        console.log("cookie is " + document.cookie);
        const response = await fetch(`${API_BASE_URL}/auth/logout/${params.get("id")}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // 清除登录状态
        localStorage.removeItem('token');
        sessionStorage.removeItem('userInfo');

        // 跳转到登录页
        window.location.href = 'login.html';
    }
}