<template>
  <div>
    <el-card class="mind-map-card">
      <div slot="header" class="title-header">
        <div class="title-content">
          <i class="el-icon-share title-icon"></i>
          <span class="title-text">风险分析思维导图</span>
          <div class="title-decoration"></div>
        </div>
      </div>
      <div class="mind-map">
        <div class="center-topic">
          <div class="circle-content">风险分析</div>
          <div class="branches">
            <!-- 左侧分支 -->
            <div class="branch left">
              <div class="main-branch">
                <span>风险识别</span>
                <div class="sub-branches">
                  <div class="sub-topic">项目背景分析</div>
                  <div class="sub-topic">历史数据收集</div>
                  <div class="sub-topic">专家经验总结</div>
                </div>
              </div>
              <div class="main-branch">
                <span>风险分类</span>
                <div class="sub-branches">
                  <div class="sub-topic">技术风险</div>
                  <div class="sub-topic">管理风险</div>
                  <div class="sub-topic">市场风险</div>
                </div>
              </div>
            </div>
            
            <!-- 右侧分支 -->
            <div class="branch right">
              <div class="main-branch">
                <span>风险评估</span>
                <div class="sub-branches">
                  <div class="sub-topic">发生概率</div>
                  <div class="sub-topic">影响程度</div>
                  <div class="sub-topic">控制难度</div>
                </div>
              </div>
              <div class="main-branch">
                <span>应对措施</span>
                <div class="sub-branches">
                  <div class="sub-topic">预防方案</div>
                  <div class="sub-topic">应急预案</div>
                  <div class="sub-topic">跟踪监控</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'PtlDa',
  data() {
    return {
      activeStep: 0
    }
  },
  methods: {
    nextStep() {
      if (this.activeStep < 4) {
        this.activeStep++
      }
    },
    prevStep() {
      if (this.activeStep > 0) {
        this.activeStep--
      }
    }
  }
}
</script>

<style scoped>
.mind-map-card {
  margin: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.mind-map {
  min-height: 600px;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.center-topic {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  z-index: 3;
}

.circle-content {
  background: linear-gradient(145deg, #409EFF, #53a8ff);
  color: white;
  width: 180px;
  height: 180px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  font-weight: bold;
  box-shadow: 0 4px 20px rgba(64, 158, 255, 0.3);
  border: 3px solid rgba(255, 255, 255, 0.2);
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  z-index: 10;
}

.circle-content::after {
  content: '';
  position: absolute;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  border: 2px dashed rgba(64, 158, 255, 0.3);
  animation: rotate 20s linear infinite;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.branches {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 800px;
  height: 500px;
}

.branch {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.left {
  right: 58%;
  margin-right: 120px;
}

.right {
  left: 58%;
  margin-left: 120px;
}

.main-branch::before {
  content: '';
  position: absolute;
  height: 3px;
  background: linear-gradient(90deg, 
    rgba(103, 194, 58, 0.2),
    rgba(103, 194, 58, 1)
  );
  filter: drop-shadow(0 2px 4px rgba(103, 194, 58, 0.2));
  transform-origin: right center;
}

/* 左侧第一个分支的连接线 */
.left .main-branch:first-child::before {
  width: 120px;
  top: 70%;
  right: -60px;
  transform: rotate(30deg);
}

/* 左侧第二个分支的连接线 */
.left .main-branch:last-child::before {
  width: 120px;
  top: 30%;
  right: -60px;
  transform: rotate(-30deg);
}

/* 右侧第一个分支的连接线 */
.right .main-branch:first-child::before {
  width: 120px;
  top: 70%;
  left: -60px;
  transform: rotate(-30deg);
  transform-origin: left center;
}

/* 右侧第二个分支的连接线 */
.right .main-branch:last-child::before {
  width: 120px;
  top: 30%;
  left: -60px;
  transform: rotate(30deg);
  transform-origin: left center;
}

/* 连接线悬停效果 */
.main-branch:hover::before {
  filter: drop-shadow(0 0 8px rgba(103, 194, 58, 0.4));
  transition: all 0.4s ease;
}

/* 移除之前的水平连接线 */
.branch::before {
  display: none;
}

/* 调整主分支间距 */
.main-branch {
  margin: 30px 0;
  background: linear-gradient(145deg, #67C23A, #95d475);
  padding: 18px 35px;
  border-radius: 30px;
  position: relative;
  box-shadow: 0 6px 20px rgba(103, 194, 58, 0.2);
  border: 2px solid rgba(255, 255, 255, 0.3);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  backdrop-filter: blur(5px);
}

.main-branch span {
  font-size: 18px;
  font-weight: 600;
  color: white;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  letter-spacing: 1px;
}

/* 增加动画效果 */
.circle-content:hover {
  transform: scale(1.1);
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px 0 rgba(64, 158, 255, 0.3);
}

.sub-branches {
  margin-top: 25px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  position: relative;
}

.sub-topic {
  background: linear-gradient(145deg, #E6A23C, #f5c57c);
  color: white;
  padding: 14px 25px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
  box-shadow: 0 4px 15px rgba(230, 162, 60, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transform-origin: left center;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  padding-left: 30px;
  backdrop-filter: blur(5px);
}

.sub-topic::before {
  content: '•';
  position: absolute;
  left: 12px;
  color: rgba(255, 255, 255, 0.9);
  font-size: 24px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.sub-topic:hover {
  transform: translateX(15px) scale(1.03);
  background: linear-gradient(145deg, #f56c6c, #fa9e9e);
  box-shadow: 0 6px 20px rgba(245, 108, 108, 0.25);
}

@keyframes branchFadeIn {
  0% { opacity: 0; transform: translateX(-20px); }
  100% { opacity: 1; transform: translateX(0); }
}

.sub-topic {
  animation: fadeInScale 0.6s cubic-bezier(0.4, 0, 0.2, 1) forwards;
  animation-delay: calc(var(--index, 0) * 0.15s);
}

.left .sub-topic {
  transform-origin: right center;
}

.right .sub-topic {
  transform-origin: left center;
}

.left .sub-topic:hover {
  transform: translateX(-10px) scale(1.02);
}

.right .sub-topic:hover {
  transform: translateX(10px) scale(1.02);
}

/* 优化动画效果 */
@keyframes fadeInScale {
  0% { 
    opacity: 0; 
    transform: scale(0.9) translateY(20px);
  }
  100% { 
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

/* 添加光晕效果 */
.circle-content::before {
  content: '';
  position: absolute;
  width: 190px;
  height: 190px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(64, 158, 255, 0.2) 0%, transparent 70%);
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0% { transform: scale(1); opacity: 0.5; }
  50% { transform: scale(1.1); opacity: 0.3; }
  100% { transform: scale(1); opacity: 0.5; }
}

.title-header {
  position: relative;
  padding: 15px 0;
  text-align: center;
  background: linear-gradient(to right, rgba(64, 158, 255, 0.1), rgba(64, 158, 255, 0.2), rgba(64, 158, 255, 0.1));
  border-radius: 8px;
  overflow: hidden;
}

.title-content {
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 0 25px;
  margin-left: 0; /* 移除负边距 */
}

.title-icon {
  font-size: 24px;
  color: #409EFF;
  animation: rotate360 20s linear infinite;
  margin-right: 15px; /* 减小图标与文字的间距 */
  position: relative; /* 添加相对定位 */
  z-index: 1; /* 确保图标在正确的层级 */
}

.title-text {
  font-size: 22px;
  font-weight: bold;
  color: #303133;
  letter-spacing: 2px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 2; /* 确保文字在图标上层 */
}

.title-text::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(to right, transparent, #409EFF, transparent);
  transform: scaleX(0);
  transform-origin: center;
  animation: lineScale 0.6s ease-out forwards;
}

.title-decoration {
  position: absolute;
  top: 50%;
  left: -20px;
  right: -20px;
  height: 1px;
  background: linear-gradient(to right, transparent, rgba(64, 158, 255, 0.3), transparent);
  z-index: 1;
}

.title-decoration::before,
.title-decoration::after {
  content: '•';
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  color: #409EFF;
  font-size: 20px;
}

.title-decoration::before {
  left: 30%;
}

.title-decoration::after {
  right: 30%;
}

@keyframes rotate360 {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes lineScale {
  from { transform: scaleX(0); }
  to { transform: scaleX(1); }
}
</style>
