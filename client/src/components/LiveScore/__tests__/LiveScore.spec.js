import { shallow } from '@vue/test-utils'
import LiveScore from '../LiveScore.vue'

describe('LiveScore', () => {
  it('renders props.msg when passed', () => {
    const msg = 'new message'
    const wrapper = shallow(LiveScore, {
      propsData: { msg }
    })
    expect(wrapper.text()).toMatch(msg)
  })
})
