<template>
    <!-- 按钮容器 - 整体布局采用flex布局，分为上下两行 -->
    <div class="button-container">
        <!-- 第一行按钮 -->
        <div class="button-row">
            <button v-for="button in firstRow" :key="button.id" class="image-button">
                <img :src="button.image" :alt="button.text" />
                <span>{{ button.text }}</span>
            </button>
        </div>
        <!-- 第二行按钮 -->
        <div class="button-row">
            <button v-for="button in secondRow" :key="button.id" class="image-button">
                <img :src="button.image" :alt="button.text" />
                <span>{{ button.text }}</span>
            </button>
        </div>
    </div>
</template>

<script>
/**
 * 设备类型选择界面组件
 * 展示不同类型的工业设备，提供图片和文字说明
 */
export default {
    name: "PtlDc",
    data() {
        return {
            // 按钮数据数组，包含图片路径和文字说明
            buttons: [
                { id: 1, image: require('@/img/icon1.jpg'), text: '超声波流量计' },
                { id: 2, image: require('@/img/icon2.jpg'), text: '电动球阀' },
                { id: 3, image: require('@/img/icon3.jpg'), text: '孔板流量计' },
                { id: 4, image: require('@/img/icon4.jpg'), text: '自立式调压阀' },
                { id: 5, image: require('@/img/icon5.jpg'), text: '旋风分离器' },
                { id: 6, image: require('@/img/icon6.jpg'), text: '过滤分离器' },
                { id: 7, image: require('@/img/icon7.jpg'), text: '手动球阀' },
                { id: 8, image: require('@/img/icon8.jpg'), text: '消防箱' }
            ],
        };
    },
    computed: {
        // 获取前4个按钮数据用于第一行显示
        firstRow() {
            return this.buttons.slice(0, 4);
        },
        // 获取后4个按钮数据用于第二行显示
        secondRow() {
            return this.buttons.slice(4, 8);
        }
    }
};
</script>

<style scoped>
/* 主容器样式 */
.button-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    padding: 40px;
    background-color: #f5f7fa;
    min-height: 100vh;
}

/* 按钮行样式 - 水平排列按钮 */
.button-row {
    display: flex;
    gap: 60px;  /* 增加间距 */
    justify-content: center;
}

/* 图片按钮样式 - 垂直排列图片和文字 */
.image-button {
    width: 300px;  /* 增加按钮宽度 */
    display: flex;
    flex-direction: column;
    align-items: center;
    border: 1px solid #e4e7ed;
    background: white;
    cursor: pointer;
    padding: 20px;
    border-radius: 8px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 按钮悬停效果 */
.image-button:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
    border-color: #409EFF;
}

/* 按钮内图片样式 */
.image-button img {
    width: 220px;  /* 增加图片宽度 */
    height: 220px; /* 增加图片高度 */
    object-fit: cover;
    border-radius: 12px; /* 稍微增加圆角 */
    margin-bottom: 20px; /* 增加底部间距 */
}

/* 按钮文字样式 */
.image-button span {
    font-size: 18px;  /* 增加文字大小 */
    color: #303133;
    font-weight: 500;
    margin-top: 10px;
}

/* 按钮文字悬停效果 */
.image-button:hover span {
    color: #409EFF;
}
</style>