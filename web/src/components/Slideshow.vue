<template>
  <div class="slideshow-container">
    <div class="slideshow-wrapper">
      <!-- Slide content -->
      <transition :name="transitionName" mode="out-in">
        <div :key="currentSlide" class="slide">
          <div class="slide-number">{{ currentSlide + 1 }} / {{ slides.length }}</div>
          <div class="slide-content">
            <h2 class="slide-title">{{ slides[currentSlide].title }}</h2>
            <p class="slide-text">{{ slides[currentSlide].content }}</p>
          </div>
        </div>
      </transition>

      <!-- Navigation buttons -->
      <div class="navigation">
        <button
          class="nav-button prev"
          @click="prevSlide"
          :disabled="currentSlide === 0"
        >
          ← Previous
        </button>

        <button
          class="nav-button next"
          @click="nextSlide"
          :disabled="currentSlide === slides.length - 1"
        >
          Next →
        </button>
      </div>

      <!-- Progress bar -->
      <div class="progress-bar">
        <div
          class="progress-fill"
          :style="{ width: progressWidth + '%' }"
        ></div>
      </div>

      <!-- Dot indicators -->
      <div class="dots">
        <span
          v-for="(slide, index) in slides"
          :key="index"
          class="dot"
          :class="{ active: index === currentSlide }"
          @click="goToSlide(index)"
        ></span>
      </div>
    </div>

    <!-- Controls -->
    <div class="controls">
      <button @click="toggleAutoPlay" class="control-button">
        {{ isAutoPlaying ? '⏸ Pause' : '▶ Play' }}
      </button>
      <button @click="resetSlideshow" class="control-button">
        ↺ Reset
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Slideshow',
  props: {
    slides: {
      type: Array,
      default: () => [
        {
          title: 'Welcome to Vue Slideshow',
          content: 'This is a beautiful, interactive slideshow built with Vue 3. Navigate using the buttons or keyboard arrows.'
        },
        {
          title: 'Feature-Rich',
          content: 'Includes smooth transitions, progress tracking, auto-play functionality, and keyboard navigation support.'
        },
        {
          title: 'Customizable',
          content: 'Easily customize slides by passing your own data. Perfect for presentations, tutorials, or showcasing content.'
        },
        {
          title: 'Mind Map Integration',
          content: 'Can be integrated with the JavaFX Mind Map application to present mind map nodes in a sequential format.'
        }
      ]
    },
    autoPlayInterval: {
      type: Number,
      default: 3000
    }
  },
  data() {
    return {
      currentSlide: 0,
      isAutoPlaying: false,
      autoPlayTimer: null,
      transitionName: 'slide-next'
    }
  },
  computed: {
    progressWidth() {
      return ((this.currentSlide + 1) / this.slides.length) * 100
    }
  },
  methods: {
    nextSlide() {
      if (this.currentSlide < this.slides.length - 1) {
        this.transitionName = 'slide-next'
        this.currentSlide++
      } else if (this.isAutoPlaying) {
        this.resetSlideshow()
      }
    },
    prevSlide() {
      if (this.currentSlide > 0) {
        this.transitionName = 'slide-prev'
        this.currentSlide--
      }
    },
    goToSlide(index) {
      this.transitionName = index > this.currentSlide ? 'slide-next' : 'slide-prev'
      this.currentSlide = index
    },
    toggleAutoPlay() {
      this.isAutoPlaying = !this.isAutoPlaying
      if (this.isAutoPlaying) {
        this.startAutoPlay()
      } else {
        this.stopAutoPlay()
      }
    },
    startAutoPlay() {
      this.autoPlayTimer = setInterval(() => {
        this.nextSlide()
      }, this.autoPlayInterval)
    },
    stopAutoPlay() {
      if (this.autoPlayTimer) {
        clearInterval(this.autoPlayTimer)
        this.autoPlayTimer = null
      }
    },
    resetSlideshow() {
      this.transitionName = 'slide-prev'
      this.currentSlide = 0
    },
    handleKeyPress(event) {
      if (event.key === 'ArrowRight') {
        this.nextSlide()
      } else if (event.key === 'ArrowLeft') {
        this.prevSlide()
      } else if (event.key === ' ') {
        event.preventDefault()
        this.toggleAutoPlay()
      }
    }
  },
  mounted() {
    window.addEventListener('keydown', this.handleKeyPress)
  },
  beforeUnmount() {
    window.removeEventListener('keydown', this.handleKeyPress)
    this.stopAutoPlay()
  }
}
</script>

<style scoped>
.slideshow-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.slideshow-wrapper {
  position: relative;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  min-height: 400px;
}

.slide {
  padding: 60px 40px;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
}

.slide-number {
  position: absolute;
  top: 20px;
  right: 30px;
  background: rgba(255, 255, 255, 0.2);
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 600;
}

.slide-content {
  max-width: 700px;
}

.slide-title {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.slide-text {
  font-size: 20px;
  line-height: 1.6;
  opacity: 0.95;
}

.navigation {
  position: absolute;
  bottom: 80px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  padding: 0 40px;
}

.nav-button {
  background: rgba(255, 255, 255, 0.9);
  color: #667eea;
  border: none;
  padding: 12px 24px;
  border-radius: 25px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.nav-button:hover:not(:disabled) {
  background: white;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
}

.nav-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.progress-bar {
  position: absolute;
  bottom: 60px;
  left: 40px;
  right: 40px;
  height: 4px;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 2px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: white;
  transition: width 0.3s ease;
  border-radius: 2px;
}

.dots {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.4);
  cursor: pointer;
  transition: all 0.3s ease;
}

.dot:hover {
  background: rgba(255, 255, 255, 0.7);
}

.dot.active {
  background: white;
  width: 30px;
  border-radius: 6px;
}

.controls {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 30px;
}

.control-button {
  background: #667eea;
  color: white;
  border: none;
  padding: 12px 30px;
  border-radius: 25px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.control-button:hover {
  background: #764ba2;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

/* Transition animations */
.slide-next-enter-active,
.slide-next-leave-active,
.slide-prev-enter-active,
.slide-prev-leave-active {
  transition: all 0.5s ease;
}

.slide-next-enter-from {
  transform: translateX(100%);
  opacity: 0;
}

.slide-next-leave-to {
  transform: translateX(-100%);
  opacity: 0;
}

.slide-prev-enter-from {
  transform: translateX(-100%);
  opacity: 0;
}

.slide-prev-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

/* Responsive design */
@media (max-width: 768px) {
  .slide-title {
    font-size: 32px;
  }

  .slide-text {
    font-size: 16px;
  }

  .navigation {
    padding: 0 20px;
  }

  .nav-button {
    padding: 10px 20px;
    font-size: 14px;
  }

  .slide {
    padding: 40px 20px;
  }
}
</style>
