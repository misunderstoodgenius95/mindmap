<template>
  <div id="app">
    <header class="app-header">
      <h1>Mind Map Slideshow</h1>
      <p>A Vue 3 Slideshow Component</p>
    </header>

    <Slideshow :slides="slides" :autoPlayInterval="4000" />

    <footer class="app-footer">
      <p>Use arrow keys to navigate • Space to play/pause • Click dots to jump to a slide</p>
    </footer>
  </div>
</template>

<script>
import Slideshow from './components/Slideshow.vue'

export default {
  name: 'App',
  components: {
    Slideshow
  },
  data() {
    return {
      slides: [
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
        },
        {
          title: 'Easy to Use',
          content: 'Simply pass an array of slide objects with title and content properties. The component handles the rest!'
        }
      ]
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  min-height: 100vh;
  padding: 20px;
}

#app {
  max-width: 1200px;
  margin: 0 auto;
}

.app-header {
  text-align: center;
  margin-bottom: 40px;
  color: #333;
}

.app-header h1 {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.app-header p {
  font-size: 18px;
  color: #666;
}

.app-footer {
  text-align: center;
  margin-top: 40px;
  color: #666;
  font-size: 14px;
}
</style>
