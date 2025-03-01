<template>
  <div class="er-mindmap">
    <div class="mindmap">
      <ul>
        <li>
          <div class="node root">应急响应流程</div>
          <ul>
            <li>
              <div class="node level1">发现与报告</div>
              <ul>
                <li><div class="node level2">安全事件发现</div></li>
                <li><div class="node level2">上报流程</div></li>
                <li><div class="node level2">初步评估</div></li>
              </ul>
            </li>
            <li>
              <div class="node level1">应急处置</div>
              <ul>
                <li><div class="node level2">成立应急小组</div></li>
                <li><div class="node level2">制定处置方案</div></li>
                <li><div class="node level2">现场处置</div></li>
              </ul>
            </li>
            <li>
              <div class="node level1">事件调查</div>
              <ul>
                <li><div class="node level2">事件分析</div></li>
                <li><div class="node level2">影响评估</div></li>
                <li><div class="node level2">根因分析</div></li>
              </ul>
            </li>
            <li>
              <div class="node level1">恢复与改进</div>
              <ul>
                <li><div class="node level2">系统恢复</div></li>
                <li><div class="node level2">经验总结</div></li>
                <li><div class="node level2">预案完善</div></li>
              </ul>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PtlEr'
}
</script>

<style scoped>
.er-mindmap {
  padding: 40px;
  background: linear-gradient(120deg, #fdfbfb 0%, #ebedee 100%);
  min-height: 100%;
  min-width: 100%;
  overflow: auto;
  border-radius: 12px;
  box-shadow: 0 0 30px rgba(0,0,0,0.05);
}

.mindmap {
  display: flex;
  align-items: center;
  padding: 60px;
  min-width: fit-content;
  background: rgba(255,255,255,0.6);
  backdrop-filter: blur(10px);
  border-radius: 16px;
}

.mindmap ul {
  display: flex;
  position: relative;
  padding: 0 1.5em;
  list-style: none;
}

.mindmap ul ul {
  flex-direction: column;
}

.mindmap ul ul::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  background: linear-gradient(180deg, transparent, #e8e8e8, transparent);
  height: 100%;
  width: 2px;
}

.mindmap li {
  position: relative;
  padding: 0 1.5em;
  display: flex;
  align-items: center;
}

.mindmap li::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  width: 1.5em;
  height: 2px;
  background: linear-gradient(90deg, transparent, #e8e8e8);
}

.mindmap > ul > li::before {
  display: none;
}

.mindmap ul ul li {
  margin: 1em 0;
}

.node {
  white-space: nowrap;
  margin: 0 1.2em;
  padding: 1em 1.8em;
  border: 2px solid transparent;
  border-radius: 12px;
  font-size: 14px;
  background: #fff;
  transition: all 0.5s cubic-bezier(0.34, 1.56, 0.64, 1);
  box-shadow: 0 4px 15px rgba(0,0,0,0.08);
  position: relative;
  overflow: hidden;
}

.node::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(120deg, rgba(255,255,255,0) 0%, rgba(255,255,255,0.2) 100%);
  opacity: 0;
  transition: opacity 0.3s;
}

.node:hover::before {
  opacity: 1;
}

.node:hover {
  background: #f0f7ff;
  border-color: #409EFF;
  transform: scale(1.08) translateY(-3px);
  cursor: pointer;
  box-shadow: 0 8px 20px rgba(64,158,255,0.15);
}

.root {
  background: linear-gradient(45deg, #409EFF, #36a3ff);
  color: white;
  font-weight: bold;
  font-size: 16px;
  letter-spacing: 1px;
  box-shadow: 0 6px 20px rgba(64,158,255,0.25);
  border: none;
}

.level1 {
  background: linear-gradient(to right, #ecf5ff, #f5f9ff);
  color: #409EFF;
  border: 2px solid rgba(64,158,255,0.2);
  font-weight: 500;
}

.level2 {
  background: #fff;
  color: #606266;
  border: 1px solid #ebeef5;
  font-weight: normal;
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.mindmap li {
  animation: fadeInRight 0.6s ease-out forwards;
  animation-delay: calc(var(--index, 0) * 0.15s);
}

.root:hover {
  background: linear-gradient(45deg, #36a3ff, #66b1ff);
  transform: scale(1.08);
  box-shadow: 0 8px 25px rgba(64,158,255,0.3);
}

.level1:hover {
  background: linear-gradient(to right, #f0f7ff, #f8faff);
  border-color: #409EFF;
}

@media (hover: hover) {
  .node:active {
    transform: scale(1.02);
    transition: transform 0.1s;
  }
}
</style>
