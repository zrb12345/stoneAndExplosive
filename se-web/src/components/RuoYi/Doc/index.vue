<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'RuoYiDoc',
  data() {
    return {
      url: 'http://doc.se.vip/se-vue'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>