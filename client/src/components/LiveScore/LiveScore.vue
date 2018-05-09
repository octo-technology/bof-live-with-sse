<template>
    <div>
        <h1>Score : {{ score }}</h1>
    </div>
</template>

<script>
export default {
  name: 'LiveScore',
  data () {
    return {
      score: 0
    }
  },
  methods: {
    updateLiveScore ({data}) {
      const parsedData = JSON.parse(data)
      this.score = parsedData.liveScore.score
    }
  },
  mounted () {
    const eventSource = new EventSource('http://localhost:8080/api/sse')
    eventSource.addEventListener('live-score', this.updateLiveScore)
  }
}
</script>
