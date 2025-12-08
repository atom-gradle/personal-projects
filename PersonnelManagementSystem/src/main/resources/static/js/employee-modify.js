// 头像相关的JavaScript函数
let currentAvatarFile = null;
let defaultAvatarUrl = '/images/default-avatar.jpg';

// 初始化头像功能
document.addEventListener('DOMContentLoaded', function() {
    const avatarPreview = document.getElementById('editEmpAvatarPreview');
    const avatarFileInput = document.getElementById('editEmpAvatarFile');
    const avatarUrlInput = document.getElementById('editEmpAvatarUrl');

    // 点击头像预览大图
    avatarPreview.addEventListener('click', function() {
        const largePreview = document.getElementById('largeAvatarPreview');
        largePreview.src = this.src;
        new bootstrap.Modal(document.getElementById('avatarPreviewModal')).show();
    });

    // 文件选择变化
    avatarFileInput.addEventListener('change', function(e) {
        if (this.files && this.files[0]) {
            currentAvatarFile = this.files[0];
            previewAvatarFile(this.files[0]);
        }
    });

    // URL输入变化
    avatarUrlInput.addEventListener('blur', previewAvatarFromUrl);
});

// 预览上传的文件
function previewAvatarFile(file) {
    const reader = new FileReader();
    const preview = document.getElementById('editEmpAvatarPreview');
    const fileInfo = document.getElementById('avatarFileInfo');

    // 显示文件信息
    const fileSize = (file.size / 1024).toFixed(2);
    fileInfo.innerHTML = `
        <div class="alert alert-info py-1 px-2 mb-0 d-inline-flex align-items-center">
            <i class="fas fa-info-circle me-2"></i>
            <span>${file.name} (${fileSize} KB)</span>
        </div>
    `;

    reader.onload = function(e) {
        preview.src = e.target.result;
        // 同时更新URL输入框（可选）
        document.getElementById('editEmpAvatarUrl').value = '';
    };

    reader.readAsDataURL(file);
}

// 从URL预览头像
function previewAvatarFromUrl() {
    const url = document.getElementById('editEmpAvatarUrl').value.trim();
    const preview = document.getElementById('editEmpAvatarPreview');
    const fileInfo = document.getElementById('avatarFileInfo');

    if (!url) return;

    if (url.match(/\.(jpeg|jpg|gif|png)$/) != null) {
        // 显示加载状态
        preview.src = '/images/loading.gif';
        fileInfo.innerHTML = `
            <div class="alert alert-warning py-1 px-2 mb-0 d-inline-flex align-items-center">
                <i class="fas fa-spinner fa-spin me-2"></i>
                <span>正在加载图片...</span>
            </div>
        `;

        // 创建图片对象测试加载
        const testImage = new Image();
        testImage.onload = function() {
            preview.src = url;
            currentAvatarFile = null;
            fileInfo.innerHTML = `
                <div class="alert alert-success py-1 px-2 mb-0 d-inline-flex align-items-center">
                    <i class="fas fa-check-circle me-2"></i>
                    <span>URL图片加载成功</span>
                </div>
            `;
        };

        testImage.onerror = function() {
            preview.src = defaultAvatarUrl;
            fileInfo.innerHTML = `
                <div class="alert alert-danger py-1 px-2 mb-0 d-inline-flex align-items-center">
                    <i class="fas fa-exclamation-circle me-2"></i>
                    <span>无法加载该URL的图片</span>
                </div>
            `;
            document.getElementById('editEmpAvatarUrl').focus();
        };

        testImage.src = url;
    } else {
        fileInfo.innerHTML = `
            <div class="alert alert-danger py-1 px-2 mb-0 d-inline-flex align-items-center">
                <i class="fas fa-exclamation-circle me-2"></i>
                <span>请输入有效的图片URL</span>
            </div>
        `;
    }
}

// 下载头像
function downloadAvatar() {
    const preview = document.getElementById('editEmpAvatarPreview');
    const empName = document.getElementById('editEmpName').value || '员工';

    // 创建下载链接
    const link = document.createElement('a');
    link.href = preview.src;
    link.download = `${empName}_avatar.jpg`;
    link.click();
}

// 恢复默认头像
function resetAvatar() {
    const preview = document.getElementById('editEmpAvatarPreview');
    preview.src = defaultAvatarUrl;
    currentAvatarFile = null;
    document.getElementById('editEmpAvatarUrl').value = '';
    document.getElementById('avatarFileInfo').innerHTML = '';
    document.getElementById('editEmpAvatarFile').value = '';

    showToast('已恢复默认头像', 'success');
}

// 清除头像
function clearAvatar() {
    if (confirm('确定要清除头像吗？')) {
        const preview = document.getElementById('editEmpAvatarPreview');
        preview.src = '/images/no-avatar.jpg'; // 可以创建一个无头像的图片
        currentAvatarFile = null;
        document.getElementById('editEmpAvatarUrl').value = '';
        document.getElementById('avatarFileInfo').innerHTML = '';
        document.getElementById('editEmpAvatarFile').value = '';

        showToast('头像已清除', 'warning');
    }
}
